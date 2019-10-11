package com.example.userprofile.Interface;

import android.os.Messenger;

/*
已用liveData替换
 */
@Deprecated
public interface HandlePosition {
    //把音乐传来的位置返回一个parcelable数据
    @Deprecated
    void sendMusicParcelable(Messenger service, int position);
}
