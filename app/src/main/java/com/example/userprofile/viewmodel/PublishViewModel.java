package com.example.userprofile.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.userprofile.model.Published;
import com.example.userprofile.repository.published.PublishFactory;

/**
 * @descriptioon:
 * @author: dinghaoyu
 * @date: 2019-09-30 12:03
 */
public class PublishViewModel extends ViewModel {
    private LiveData<PagedList<Published>> mPushlished;

    public PublishViewModel() {

        PublishFactory publishFactory = new PublishFactory();

        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(12)
                .setInitialLoadSizeHint(20)
                .setEnablePlaceholders(false)
                .build();

        mPushlished = new LivePagedListBuilder<String,Published>(publishFactory,config).build();
    }

    public LiveData<PagedList<Published>> getPushlished() {
        return mPushlished;
    }
}
