package com.example.userprofile.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.PowerManager;
import android.os.RemoteException;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.userprofile.Interface.MusicChange;
import com.example.userprofile.R;
import com.example.userprofile.Utils.HttpUtil;
import com.example.userprofile.model.MusicParcelable;
import com.example.userprofile.view.UserProfileActivity;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.app.Notification.BADGE_ICON_SMALL;

/**
 * @descriptioon:
 * @author: dinghaoyu
 * @date: 2019-09-30 16:36
 */
public class MusicService extends Service {
    private static final int CONTENT_PENDINGINTENT_REQUESTCODE = 1;
    private static final int NEXT_PENDINGINTENT_REQUESTCODE = 2;
    private static final int PLAY_PENDINGINTENT_REQUESTCODE = 3;
    private static final int STOP_PENDINGINTENT_REQUESTCODE = 4;
    private static final int NOTIFICATION_PENDINGINTENT_ID = 1;// 是用来标记Notifaction，可用于更新，删除Notifition
    private static final int MSG_TO_CLIENT = 2;
    private static final String CHANNELID = "channel1";
    private static final String CHANNEL_NAME = "channel1";
    private static final String CONTENT_URI = "content://com.example.hotsoon.provider/music";

    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;
    private RemoteViews views;
    private BroadcastReceiver playerReceiver;

    private MediaPlayer mediaPlayer;
    private WifiManager.WifiLock wifiLock;

    private String musics;// 设置音频资源（网络）
    private boolean isPause = false;



    private Messenger mClient;
    private MusicParcelable mMusicParcelable;
    private static final String TAG = "MusicService";
    private Messenger mMessenger;
    private Handler imgHandler;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate: ");

        initMediaPlayer();


        // 设置点击通知结果
        Intent intent = new Intent(this, UserProfileActivity.class);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel channel = new NotificationChannel(CHANNELID,
                CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        channel.setShowBadge(true); //设置是否显示角标
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);//设置是否应在锁定屏幕上显示此频道的通知
        channel.setBypassDnd(true);//设置是否绕过免打扰模式
        notificationManager.createNotificationChannel(channel);
        builder = new NotificationCompat.Builder(this, CHANNELID);
        builder.setBadgeIconType(BADGE_ICON_SMALL);//设置显示角标的样式


        PendingIntent contentPendingIntent = PendingIntent.getActivity(this, CONTENT_PENDINGINTENT_REQUESTCODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        // 自定义布局
        views = new RemoteViews(getPackageName(), R.layout.music_play);
        // 下一首
        Intent intentNext = new Intent("nextMusic");
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(this, NEXT_PENDINGINTENT_REQUESTCODE, intentNext, PendingIntent.FLAG_CANCEL_CURRENT);
        views.setOnClickPendingIntent(R.id.btn_next, nextPendingIntent);

        //上一曲
        Intent intentPre = new Intent("preMusic");
        PendingIntent prePendingIntent = PendingIntent.getBroadcast(this, NEXT_PENDINGINTENT_REQUESTCODE, intentPre, PendingIntent.FLAG_CANCEL_CURRENT);
        views.setOnClickPendingIntent(R.id.btn_pre, prePendingIntent);

        // 暂停/播放
        Intent intentPlay = new Intent("playMusic");
        PendingIntent playPendingIntent = PendingIntent.getBroadcast(this, PLAY_PENDINGINTENT_REQUESTCODE, intentPlay, PendingIntent.FLAG_CANCEL_CURRENT);
        views.setOnClickPendingIntent(R.id.btn_play, playPendingIntent);

        // 停止
        Intent intentStop = new Intent("stopMusic");
        PendingIntent stopPendingIntent = PendingIntent.getBroadcast(this, STOP_PENDINGINTENT_REQUESTCODE, intentStop, PendingIntent.FLAG_CANCEL_CURRENT);
        views.setOnClickPendingIntent(R.id.btn_stop, stopPendingIntent);

        builder = new NotificationCompat.Builder(this,CHANNELID)
                // 设置小图标
                .setSmallIcon(R.drawable.icon_myprofile_location)
                // 设置标题
                .setContentTitle("PlayMusic")
                // 设置内容
                .setContentText("内容")
                // 点击通知后自动清除
                .setAutoCancel(false)
                // 设置点击通知效果
                .setContentIntent(contentPendingIntent)
                // 自定义视图
                .setCustomBigContentView(views);




        startForeground(NOTIFICATION_PENDINGINTENT_ID, builder.build());



        // 注册广播
        playerReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("action", intent.getAction());
                switch (intent.getAction()) {
                    case "nextMusic":// 下一首
                        nextMusic();
                        break;

                    case "preMusic":
                        preMusic();
                        break;
                    case "playMusic":// 暂停/播放
                        if (mediaPlayer != null) {
                            if (!isPause) {
                                mediaPlayer.pause();
                                isPause = true;
                            } else {
                                mediaPlayer.start();
                                isPause = false;
                            }
                        }
                        break;
                    case "stopMusic":// 停止
                        onDestroy();
                        break;
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("nextMusic");
        intentFilter.addAction("preMusic");
        intentFilter.addAction("playMusic");
        intentFilter.addAction("stopMusic");
        registerReceiver(playerReceiver, intentFilter);

    }


    @Override
    public void onDestroy() {
        Log.d(TAG, "service destroy success");
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (wifiLock != null && wifiLock.isHeld())
            wifiLock.release();
        try {
            if (playerReceiver != null)
                unregisterReceiver(playerReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 取消Notification
        if (notificationManager != null)
            notificationManager.cancel(NOTIFICATION_PENDINGINTENT_ID);
        stopForeground(true);
        // 停止服务
        stopSelf();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 得到键值对
        boolean playing = intent.getBooleanExtra("playing", false);
        if (playing)
            play();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return mMessenger.getBinder();
    }

    // 初始化MediaPlayer
    private void initMediaPlayer() {
        //处理客户端发来的数据
        MusicHandler musicHandler = new MusicHandler();

        musicHandler.setMusicChange(new MusicChange() {
            @Override
            public void musicPlay(Messenger messenger, MusicParcelable musicParcelable) {
                mClient = messenger;
                mMusicParcelable = musicParcelable;
                musics = mMusicParcelable.getMusicUrl();
                play();
            }
        });


        mMessenger = new Messenger(musicHandler);

        Log.d(TAG, "initMediaPlayer: thread"+Thread.currentThread().getId());

        if (mediaPlayer == null)
            mediaPlayer = new MediaPlayer();

        // 设置音量，参数分别表示左右声道声音大小，取值范围为0~1
        mediaPlayer.setVolume(0.5f, 0.5f);

        // 设置是否循环播放
        mediaPlayer.setLooping(false);

        // 设置设备进入锁状态模式-可在后台播放或者缓冲音乐-CPU一直工作
        mediaPlayer.setWakeMode(this, PowerManager.PARTIAL_WAKE_LOCK);

        // 如果你使用wifi播放流媒体，你还需要持有wifi锁
        wifiLock = ((WifiManager) Objects.requireNonNull(getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE)))
                .createWifiLock(WifiManager.WIFI_MODE_FULL, "wifilock");
        wifiLock.acquire();

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
                isPause = false;
            }
        });

        // 设置播放错误监听
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                mediaPlayer.reset();
                return false;
            }
        });

        // 设置播放完成监听
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

            }
        });
    }

    // 播放
    private void play() {
        Log.d(TAG, "play: "+musics);

        try {
            if (mediaPlayer == null)
                initMediaPlayer();
            if (isPause) {
                mediaPlayer.start();
                isPause = false;
            } else {
                // 重置mediaPlayer
                mediaPlayer.reset();
                // 重新加载音频资源
                mediaPlayer.setDataSource(musics);
                // 准备播放（异步）
                mediaPlayer.prepareAsync();
            }
            updateNotification();
            ContentValues contentValues = new ContentValues();
            contentValues.put("title",mMusicParcelable.getMusicTitle());
            contentValues.put("url",mMusicParcelable.getMusicUrl());
            contentValues.put("cover",mMusicParcelable.getMusicCoverurl());
            contentValues.put("position",mMusicParcelable.getPosition());

            getContentResolver().insert(Uri.parse(CONTENT_URI),contentValues);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // 下一首
    private void nextMusic() {
        playChangeMusic(mMusicParcelable.getPosition()+1);
    }


    //上一首
    private void preMusic() {
        playChangeMusic(mMusicParcelable.getPosition()-1);
    }

    // 更新Notification
    private void updateNotification() {
        if (views != null) {
            views.setTextViewText(R.id.tx_music_title, mMusicParcelable.getMusicTitle());

            loadBitMap(mMusicParcelable.getMusicCoverurl());

            Log.d(TAG, "updateNotification: "+mMusicParcelable.getMusicCoverurl());
            if (!isPause) {
                //更新播放按钮

            } else {

            }
        }

        // 刷新notification
        notificationManager.notify(NOTIFICATION_PENDINGINTENT_ID, builder.build());
    }


    //把需要播放音乐的位置发送给MainActivity

    public void playChangeMusic(int position){

        Message replyMsg = Message.obtain(null, MSG_TO_CLIENT);
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        replyMsg.setData(bundle);
        try
        {
            mClient.send(replyMsg);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }




    //加载网络图片
    public void loadBitMap(String url) {

        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG,"on img load response");
                Message message=imgHandler.obtainMessage();//声明一个传递信息的Message
                if (response.isSuccessful()){//成功
                    message.what=11;  //设置成功的指令为11
                    message.obj=response.body().bytes();//带入图片的数据
                    imgHandler.sendMessage(message);//将指令和数据传出去
                }else{//失败
                    imgHandler.sendEmptyMessage(10);//设置其他指令为零，然后进入handler
                }
            }
        });

        imgHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Log.d(TAG,"on message get");
                switch (msg.what){
                    case 11://成功
                        byte[]result= (byte[]) msg.obj;
                        Bitmap bitmap= BitmapFactory.decodeByteArray(result,0,result.length);//利用BitmapFactory将数据转换成bitmap类型
                        views.setImageViewBitmap(R.id.img_music_cover,bitmap);
                        notificationManager.notify(NOTIFICATION_PENDINGINTENT_ID, builder.build());
                }
                return false;
            }
        });

    }

}
