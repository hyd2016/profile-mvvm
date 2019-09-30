package com.example.userprofile.repository.recommend;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.example.userprofile.model.RecommendUser;

/**
 * @descriptioon:
 * @author: dinghaoyu
 * @date: 2019-09-29 20:52
 */
public class RecommendFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<String, RecommendUser>> mPageKeyedDataSourceMutableLiveData = new MutableLiveData<>();

    @NonNull
    @Override
    public DataSource create() {
        RecommenDataSource recommenDataSource = new RecommenDataSource();
        mPageKeyedDataSourceMutableLiveData.postValue(recommenDataSource);
        return recommenDataSource;
    }
}
