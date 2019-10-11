package com.example.userprofile.di;

import com.example.userprofile.adapter.PublishAdapter;
import com.example.userprofile.adapter.RecommendAdapter;
import com.example.userprofile.repository.WebServer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @descriptioon:
 * @author: dinghaoyu
 * @date: 2019-10-05 22:56
 */

@Module
public abstract class UserProfileModule {

    @Singleton
    @Provides
    public static WebServer providerWebServer(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.huoshan.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(WebServer.class);
    }

    @Singleton
    @Provides
    public static PublishAdapter providerPulishAdapter(){
        return new PublishAdapter();
    }

    @Singleton
    @Provides
    public static RecommendAdapter providerRecommendAdapter(){
        return new RecommendAdapter();
    }


}
