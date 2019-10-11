package com.example.userprofile.repository.published;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.example.userprofile.model.Published;

/**
 * @descriptioon:
 * @author: dinghaoyu
 * @date: 2019-09-30 11:58
 */
public class PublishFactory extends DataSource.Factory<String,Published> {

    @NonNull
    @Override
    public DataSource create() {
        return new PublishDataSource();
    }
}
