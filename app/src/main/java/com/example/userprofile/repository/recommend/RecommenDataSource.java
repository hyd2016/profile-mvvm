package com.example.userprofile.repository.recommend;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.userprofile.di.DaggerUserProfileComponent;
import com.example.userprofile.model.RecommendPackage;
import com.example.userprofile.model.RecommendUser;
import com.example.userprofile.repository.WebServer;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @descriptioon:
 * @author: dinghaoyu
 * @date: 2019-09-29 17:14
 */
public class RecommenDataSource extends PageKeyedDataSource<String, RecommendUser> {
    private static final String TAG = "RecommenDataSource";

    @Inject
    WebServer mWebServer;

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull LoadInitialCallback<String, RecommendUser> callback) {

        DaggerUserProfileComponent.create().inject(this);

        mWebServer.getRecommendList("3724749997616891").enqueue(new Callback<RecommendPackage>() {
            @Override
            public void onResponse(Call<RecommendPackage> call, Response<RecommendPackage> response) {
                if (response.body() != null) {
                    Log.d(TAG, "onResponse: get message"+response.message());
                    callback.onResult(response.body().getRecommendUsers(),null,null);
                }
            }

            @Override
            public void onFailure(Call<RecommendPackage> call, Throwable t) {
                Log.e(TAG, "onFailure: "+ t.toString());

                StackTraceElement[] stackTraceElement = t.getStackTrace();
                for (int i = 0;i<stackTraceElement.length;i++){
                    Log.e(TAG, stackTraceElement[i].toString());
                }
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
