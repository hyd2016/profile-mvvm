package com.example.userprofile.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.userprofile.Utils.IPCByMessenger;
import com.example.userprofile.di.DaggerUserProfileComponent;
import com.example.userprofile.model.MusicParcelable;
import com.example.userprofile.model.Published;
import com.example.userprofile.repository.published.PublishFactory;

import javax.inject.Inject;

/**
 * @descriptioon:
 * @author: dinghaoyu
 * @date: 2019-09-30 12:03
 */
public class PublishViewModel extends ViewModel {
    private static final String TAG = "PublishViewModel";
    private Context mContext;

    //private PositionHandler positionHandler = new PositionHandler();

    //private Messenger mClient = new Messenger(positionHandler);

    private LiveData<PagedList<Published>> mPushlished;

    //private MusicServiceConnection mMusicServiceConnection;

    //private MusicParcelable mMusicParcelable;


    @Inject
    IPCByMessenger mIPCByMessenger;

    public PublishViewModel() {

        PublishFactory publishFactory = new PublishFactory();

        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(12)
                .setInitialLoadSizeHint(20)
                .setEnablePlaceholders(false)
                .build();

        mPushlished = new LivePagedListBuilder<>(publishFactory,config).build();

        DaggerUserProfileComponent.create().inject(PublishViewModel.this);



//        positionHandler.setmHandlePosition(new HandlePosition() {
//            @Override
//            public void sendMusicParcelable(Messenger service,int position) {
//                Log.d(TAG, "sendMusicParcelable: "+ position);
//
//                if (mPushlished.getValue() != null){
//                    //上一曲到0
//                    if(position < 0){
//                        position = mPushlished.getValue().size()-1;
//                    }
//                    if (position>=mPushlished.getValue().size()){
//                        position = 0;
//                    }
//
//                }
//
//                Intent intent = new Intent(mContext, MusicService.class);
//
//                mMusicParcelable = generateMusicParcelable(position);
//
//                mMusicServiceConnection = new MusicServiceConnection(mMusicParcelable,mClient);
//
//                Log.d(TAG, "sendMusicParcelable: ");
//
//                mContext.bindService(intent,mMusicServiceConnection,BIND_AUTO_CREATE);
//
//            }
//        });
        Log.d(TAG, "PublishViewModel: "+mIPCByMessenger.toString());


    }

    public void init(Context context){
        mContext = context;

        //对service发来位置数据进行监听
        mIPCByMessenger.getPositionHandler().getPosition().observe((LifecycleOwner) mContext, new Observer<Integer>() {
            @Override
            public void onChanged(Integer position) {
                if (mPushlished.getValue() != null){
                    //上一曲到0
                    if(position < 0){
                        position = mPushlished.getValue().size()-1;
                    }
                    if (position>=mPushlished.getValue().size()){
                        position = 0;
                    }

                }

                startService(position);
            }
        });
    }

    //生成MusicParcelable对象
    private MusicParcelable generateMusicParcelable(int position){
        if (mPushlished.getValue() == null){
            return null;
        }

        Published published = mPushlished.getValue().get(position);
        Log.d(TAG, "generateMusicParcelable: "+published.getPublishDataData().toString());
        String musicUrl = published.getPublishDataData().getSong().getPlayUrl().getUrlList().get(0);
        String musicTitle = published.getPublishDataData().getSong().getTitle();
        String musicCover = published.getPublishDataData().getSong().getCoverThumb().getUrlList().get(0);

        //通过parcelable向service发送数据

        return new MusicParcelable(musicUrl,musicTitle,musicCover,position);

    }

    public LiveData<PagedList<Published>> getPushlished() {
        return mPushlished;
    }

    //启动服务
    public void startService(int position){

        mIPCByMessenger.sendToService(mContext,generateMusicParcelable(position));

//        Intent intent = new Intent(mContext, MusicService.class);
//
//        mMusicParcelable = generateMusicParcelable(position);
//
//        mMusicServiceConnection = new MusicServiceConnection(mMusicParcelable,mClient);

        Log.d(TAG, "startService: " );
//        mContext.bindService(intent,mMusicServiceConnection,BIND_AUTO_CREATE);
    }

    //解绑服务服务，防止内存泄漏
    public void unBindMusicService(){
        mIPCByMessenger.unBindService();
    }
}
