package com.example.userprofile.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.userprofile.model.User;
import com.example.userprofile.repository.UserProfileRepository;

/**
 * @descriptioon:
 * @author: dinghaoyu
 * @date: 2019-09-27 10:07
 */
public class UserProfileViewModel extends ViewModel {

    private MutableLiveData<User> currentUser;

    private MutableLiveData<Integer> followStatus;

    //获取初始化数据
    public void init(){
        currentUser = UserProfileRepository.getInstance().getUser();
        followStatus = new MutableLiveData<>();
    }

    public MutableLiveData<User> getCurrentUser() {
        return currentUser;
    }

    public MutableLiveData<Integer> getFollowStatus() {
        return followStatus;
    }
}
