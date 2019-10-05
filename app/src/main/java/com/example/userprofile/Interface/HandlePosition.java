package com.example.userprofile.Interface;

import android.os.Messenger;

public interface HandlePosition {
    //把音乐传来的位置返回一个parcelable数据
    void sendMusicParcelable(Messenger service, int position);
}
