package com.example.userprofile.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.example.userprofile.model.MusicParcelable;

/**
 * @descriptioon:
 * @author: dinghaoyu
 * @date: 2019-10-05 16:00
 */
public class MusicHistorryViewModel extends ViewModel {
    private LiveData<PagedList<MusicParcelable>> musicHistoryList;


}
