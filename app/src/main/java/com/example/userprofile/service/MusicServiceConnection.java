package com.example.userprofile.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.example.userprofile.model.MusicParcelable;

public class MusicServiceConnection implements ServiceConnection {

    private static final int SEND_TO_SERVER = 0;
    private MusicParcelable mMusicParcelable;
    private Messenger messenger;
    private static final String TAG = "MusicHandler";
    private Messenger mClient;



    public MusicServiceConnection(MusicParcelable mMusicParcelable, Messenger mClient) {
        this.mMusicParcelable = mMusicParcelable;
        this.mClient = mClient;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        messenger = new Messenger(service);
        Message message = Message.obtain(null,SEND_TO_SERVER,0,0);
        Bundle bundle = new Bundle();
        bundle.putParcelable("music",mMusicParcelable);

        message.replyTo = mClient;

        message.setData(bundle);
        try{
            messenger.send(message);
        }catch (RemoteException e){
            e.printStackTrace();
            Log.d(TAG, "onServiceConnected: "+e);
        }

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
