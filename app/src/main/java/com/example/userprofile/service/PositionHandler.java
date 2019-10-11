package com.example.userprofile.service;

import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.userprofile.Interface.HandlePosition;

public class PositionHandler extends Handler {
    private static final String TAG = "PositionHandler";
    private static final int MSG_FROM_SERVER = 2;

    private HandlePosition mHandlePosition;

    private MutableLiveData<Integer> mLiveDataPosition = new MutableLiveData<>();

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            case MSG_FROM_SERVER:

                int position = msg.getData().getInt("position");

                Log.d(TAG, "handleMessage: "+ position);
                Messenger messenger = msg.replyTo;

                mLiveDataPosition.setValue(position);

//                //去主线程处理数据
//                if (mHandlePosition != null) {
//                    mHandlePosition.sendMusicParcelable(messenger, position);
//                }
                break;
            default:
                super.handleMessage(msg);
                break;
        }


    }
    public void setmHandlePosition(HandlePosition mHandlePosition) {
        this.mHandlePosition = mHandlePosition;
    }

    public MutableLiveData<Integer> getPosition() {
        return mLiveDataPosition;
    }
}
