package com.example.userprofile.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.Messenger;
import android.util.Log;

import com.example.userprofile.model.MusicParcelable;
import com.example.userprofile.service.MusicService;
import com.example.userprofile.service.MusicServiceConnection;
import com.example.userprofile.service.PositionHandler;

import javax.inject.Inject;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * @descriptioon:
 * @author: dinghaoyu
 * @date: 2019-10-11 14:17
 */
public class IPCByMessenger {

    private static final String TAG = "IPCByMessenger";

    private Messenger mMessenger;
    private PositionHandler positionHandler;
    private MusicServiceConnection mMusicServiceConnection;
    private Context mContext;


    @Inject
    public IPCByMessenger() {
        positionHandler = new PositionHandler();
        mMessenger = new Messenger(positionHandler);
    }


    public void sendToService(Context context,MusicParcelable musicParcelable){

        mContext = context;

        Intent intent = new Intent(context, MusicService.class);

        mMusicServiceConnection = new MusicServiceConnection(musicParcelable,mMessenger);

        Log.d(TAG, "sendMusicParcelable: ");

        mContext.bindService(intent,mMusicServiceConnection,BIND_AUTO_CREATE);

    }

    public PositionHandler getPositionHandler() {
        return positionHandler;
    }


    //解绑服务，防止内存泄漏
    public void unBindService(){
        if (mContext != null){
            mContext.unbindService(mMusicServiceConnection);
        }
    }
}
