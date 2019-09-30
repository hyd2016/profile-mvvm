package com.example.userprofile.repository.published;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.example.userprofile.model.Published;

/**
 * @descriptioon:
 * @author: dinghaoyu
 * @date: 2019-09-30 11:58
 */
public class PublishFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<String, Published>> mPublishDataSourceMutableLiveData
            = new MutableLiveData<>();

    @NonNull
    @Override
    public DataSource create() {
        PublishDataSource publishDataSource = new PublishDataSource();
        mPublishDataSourceMutableLiveData.postValue(publishDataSource);
        return publishDataSource;
    }
}
