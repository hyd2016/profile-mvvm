package com.example.userprofile.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.userprofile.model.Published;
import com.example.userprofile.model.User;
import com.example.userprofile.repository.UserProfileRepository;

import java.util.List;

/**
 * @descriptioon:
 * @author: dinghaoyu
 * @date: 2019-09-27 10:07
 */
public class UserProfileViewModel extends ViewModel {
    private MutableLiveData<List<Published>> currentPublishs;
    private MutableLiveData<User> currentUser;

    //获取初始化数据
    public void init(){
        currentUser = UserProfileRepository.getInstance().getUser();
        currentPublishs = UserProfileRepository.getInstance().getPublishes();
    }

    public MutableLiveData<List<Published>> getCurrentPublishs() {
        return currentPublishs;
    }

    public MutableLiveData<User> getCurrentUser() {
        return currentUser;
    }

}
