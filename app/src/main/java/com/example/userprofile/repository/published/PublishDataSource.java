package com.example.userprofile.repository.published;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.userprofile.model.Published;
import com.example.userprofile.model.PublishedPackage;
import com.example.userprofile.repository.WebServer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @descriptioon:
 * @author: dinghaoyu
 * @date: 2019-09-30 11:33
 */
public class PublishDataSource extends PageKeyedDataSource<String, Published> {
    private static final String TAG = "PublishDataSource";
    private WebServer mWebServer;

    public PublishDataSource() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.huoshan.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mWebServer = retrofit.create(WebServer.class);
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull LoadInitialCallback<String, Published> callback) {

        mWebServer.getPublishList("0").enqueue(new Callback<PublishedPackage>() {
            @Override
            public void onResponse(Call<PublishedPackage> call, Response<PublishedPackage> response) {
                if (response.body() != null){
                    Log.d(TAG, "onResponse: "+response.body().getPublishedList().get(0).getPublishDataData().getTitle());
                    callback.onResult(response.body().getPublishedList(),null,
                            response.body().getPublishExtra().getMaxTime());
                }
            }

            @Override
            public void onFailure(Call<PublishedPackage> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Published> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Published> callback) {

        mWebServer.getPublishList(params.key).enqueue(new Callback<PublishedPackage>() {
            @Override
            public void onResponse(Call<PublishedPackage> call, Response<PublishedPackage> response) {
                if (response.body() != null){
//                    Log.d(TAG, "onResponse: "+response.body().getPublishedList().get(0).getPublishDataData().getTitle());
                    callback.onResult(response.body().getPublishedList(),
                            response.body().getPublishExtra().getMaxTime());
                }
            }

            @Override
            public void onFailure(Call<PublishedPackage> call, Throwable t) {

            }
        });
    }
}
