package com.example.userprofile.repository.music;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.example.userprofile.model.MusicParcelable;

/**
 * @descriptioon:
 * @author: dinghaoyu
 * @date: 2019-10-08 13:36
 */
public class MusicHistoryFactory extends DataSource.Factory<Integer, MusicParcelable> {
    private String title;

    private MusicHistoryDataSource mMusicHistoryDataSource;

    public MusicHistoryFactory(String title,int loadSize) {
        this.title = title;
        mMusicHistoryDataSource = new MusicHistoryDataSource(title,loadSize);
    }

    @NonNull
    @Override
    public DataSource create() {
        return mMusicHistoryDataSource;
    }
}
