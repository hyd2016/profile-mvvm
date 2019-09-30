package com.example.userprofile.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.userprofile.model.RecommendUser;
import com.example.userprofile.repository.recommend.RecommendFactory;

/**
 * @descriptioon:
 * @author: dinghaoyu
 * @date: 2019-09-29 20:30
 */
public class RecommendViewModel extends ViewModel {


    private LiveData<PagedList<RecommendUser>> recommendList;

    public RecommendViewModel() {

        RecommendFactory recommendFactory = new RecommendFactory();

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(10)
                .build();
        recommendList = new LivePagedListBuilder<String,RecommendUser>(recommendFactory,config).build();

    }
    public LiveData<PagedList<RecommendUser>> getRecommendList() {
        if (recommendList != null){
            return recommendList;
        }
        return null;
    }

}
