package com.example.userprofile.repository.recommend;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.userprofile.model.RecommendPackage;
import com.example.userprofile.model.RecommendUser;
import com.example.userprofile.repository.WebServer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @descriptioon:
 * @author: dinghaoyu
 * @date: 2019-09-29 17:14
 */
public class RecommenDataSource extends PageKeyedDataSource<String, RecommendUser> {
    private static final String TAG = "RecommenDataSource";

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull LoadInitialCallback<String, RecommendUser> callback) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.huoshan.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebServer webServer = retrofit.create(WebServer.class);
        webServer.getRecommendList("3724749997616891").enqueue(new Callback<RecommendPackage>() {
            @Override
            public void onResponse(Call<RecommendPackage> call, Response<RecommendPackage> response) {
                if (response.body() != null) {
                    callback.onResult(response.body().getRecommendUsers(),null,null);
                }
            }

            @Override
            public void onFailure(Call<RecommendPackage> call, Throwable t) {

            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, RecommendUser> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, RecommendUser> callback) {

    }
}
