package com.example.userprofile.Interface;

import android.os.Messenger;

import com.example.userprofile.model.MusicParcelable;

public interface MusicChange {
    void musicPlay(Messenger messenger, MusicParcelable musicParcelable);
}
