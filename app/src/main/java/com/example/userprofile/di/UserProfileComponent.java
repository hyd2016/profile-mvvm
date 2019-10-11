package com.example.userprofile.di;

import com.example.userprofile.repository.UserProfileRepository;
import com.example.userprofile.repository.published.PublishDataSource;
import com.example.userprofile.repository.recommend.RecommenDataSource;
import com.example.userprofile.view.MusicHistoryFragment;
import com.example.userprofile.view.SearchFragment;
import com.example.userprofile.view.UserProfileActivity;
import com.example.userprofile.viewmodel.PublishViewModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @descriptioon:
 * @author: dinghaoyu
 * @date: 2019-10-06 11:29
 */

@Singleton
@Component(modules = {UserProfileModule.class})
public interface UserProfileComponent{
    void inject(UserProfileRepository userProfileRepository);
    void inject(PublishDataSource publishDataSource);
    void inject(RecommenDataSource recommenDataSource);
    void inject(UserProfileActivity userProfileActivity);
    void inject(PublishViewModel publishViewModel);
    void inject(MusicHistoryFragment musicHistoryFragment);
    void inject(SearchFragment searchFragment);
}
