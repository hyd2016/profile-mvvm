package com.example.userprofile.repository.recommend;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.example.userprofile.model.RecommendUser;

/**
 * @descriptioon:
 * @author: dinghaoyu
 * @date: 2019-09-29 20:52
 */
public class RecommendFactory extends DataSource.Factory<String,RecommendUser> {

    @NonNull
    @Override
    public DataSource create() {
        return new RecommenDataSource();
    }
}
